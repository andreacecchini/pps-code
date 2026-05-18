package it.unibo.abstractModelling


trait Exams:
  // Abstract types
  type Student
  type Teacher
  type Course
  type Evaluation
  trait Session:
    def id: Int
  // Defined types
  case class Exam(course: Course, session: Session, student: Student)
  type Registration = (Exam, Evaluation)
  // Factories
  def student(name: String): Student
  def teacher(name: String): Teacher
  def course(name: String): Course
  def evaluation(grade: Int): Evaluation
  def session(id: Int): Session
  // Methods
  def register(registration: Registration): Unit
  def evaluation(student: Student, course: Course): Evaluation
end Exams

trait AbstractExams extends Exams:
  this: ExamsRepository =>

  override def register(registration: Registration): Unit =
    store(registration)

  given Ordering[Session] = Ordering.by(_.id)
  override def evaluation(student: Student, course: Course): Evaluation =
    load(student, course).maxBy(_._1)._2

trait BasicExams extends AbstractExams:
  this: ExamsRepository =>
  // Types
  override opaque type Student = String
  override opaque type Teacher = String
  override opaque type Course = String
  override opaque type Evaluation = Int
  private case class SessionFromId(id: Int) extends Session
  // Factories
  def student(name: String): Student = name
  def teacher(name: String): Teacher = name
  def course(name: String): Course = name
  def evaluation(grade: Int): Evaluation = grade
  def session(id: Int): Session = SessionFromId(id)

trait ExamsRepository:
  this: Exams =>

  def store(registration: Registration): Unit
  def load(student: Student, course: Course): Set[(Session, Evaluation)]

trait InMemoryRepository extends ExamsRepository:
  this: Exams =>
  private var registrations: Set[Registration] = Set.empty

  override def store(registration: Registration): Unit =
    registrations += registration

  override def load(student: Student, course: Course): Set[(Session, Evaluation)] =
    registrations.collect:
      case (Exam(`course`, session, `student`), evaluation) => (session, evaluation)

@main def workWithExams(): Unit =
  val exams: Exams = new BasicExams with InMemoryRepository
  import exams.*
  val c = course("oop")
  val s = student("mirko")
  register(Exam(c, session(0), s), evaluation(20))
  register(Exam(c, session(1), s), evaluation(25))
  register(Exam(c, session(2), s), evaluation(30))
  println:
    exams.evaluation(s, c)
