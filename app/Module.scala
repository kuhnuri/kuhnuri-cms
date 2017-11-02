import com.google.inject.AbstractModule
import services._

class Module extends AbstractModule {

  override def configure() = {
//    bind(classOf[Queue]).to(classOf[DBQueue])
//    bind(classOf[Dispatcher]).to(classOf[DBQueue])
    bind(classOf[Store]).to(classOf[FileStore])
//    bind(classOf[Dispatcher]).to(classOf[SimpleQueue])
//    bind(classOf[LogStore]).to(classOf[SimpleLogStore])
  }

}
