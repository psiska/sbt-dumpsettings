import sbt._, Keys._

package object sbtdumpsettings {
  type DumpSettingsKey = DumpSettingsKey.Entry[_]
  object DumpSettingsKey {
    implicit def setting[A](key: SettingKey[A]): Entry[A] = Setting(key)
    implicit def task[A](key: TaskKey[A]): Entry[A] = Task(key)
    implicit def constant[A: Manifest](tuple: (String, A)): Entry[A] = Constant(tuple)

    def apply[A](key: SettingKey[A]): Entry[A] = Setting(key)
    def apply[A](key: TaskKey[A]): Entry[A] = Task(key)
    def apply[A: Manifest](tuple: (String, A)): Entry[A] = Constant(tuple)
    def map[A, B: Manifest](from: Entry[A])(fun: ((String, A)) => (String, B)): Entry[B] =
      DumpSettingsKey.Mapped(from, fun)
    def action[A: Manifest](name: String)(fun: => A): Entry[A] = Action(name, () => fun)

    private[sbtdumpsettings] final case class Setting[A](scoped: SettingKey[A]) extends Entry[A] {
      def manifest = scoped.key.manifest
    }
    private[sbtdumpsettings] final case class Task[A](scoped: TaskKey[A]) extends Entry[A] {
      def manifest = scoped.key.manifest.typeArguments.head.asInstanceOf[Manifest[A]]
    }

    private[sbtdumpsettings] final case class Constant[A](tuple: (String, A))(implicit val manifest: Manifest[A])
      extends Entry[A]

    private[sbtdumpsettings] final case class Mapped[A, B](from: Entry[A], fun: ((String, A)) => (String, B))(implicit val manifest: Manifest[B])
        extends Entry[B]

    private[sbtdumpsettings] final case class Action[A](name: String, fun: () => A)(implicit val manifest: Manifest[A])
      extends Entry[A]

    sealed trait Entry[A] {
      private[sbtdumpsettings] def manifest: Manifest[A]
    }
  }
}
