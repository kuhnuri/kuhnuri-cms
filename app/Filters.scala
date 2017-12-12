import javax.inject._

import play.filters.cors.CORSFilter

//import filters.TokenAuthorizationFilter
import play.api._
import play.api.http.DefaultHttpFilters

@Singleton
class Filters @Inject()(env: Environment,
//                        tokenAuthorizationFilter: TokenAuthorizationFilter,
                        corsFilter: CORSFilter
                       )
  extends DefaultHttpFilters(corsFilter
//    tokenAuthorizationFilter
  ) {
}
