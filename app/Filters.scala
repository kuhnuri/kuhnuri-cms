import javax.inject._

//import filters.TokenAuthorizationFilter
import play.api._
import play.api.http.DefaultHttpFilters

@Singleton
class Filters @Inject()(env: Environment
//                        , tokenAuthorizationFilter: TokenAuthorizationFilter
                       )
  extends DefaultHttpFilters(
//    tokenAuthorizationFilter
  ) {
}
