package com.eidoscode.filters

import play.api.mvc._
import play.api._

/**
 * Filter to be used to log all the fields from a #{RequestHeader}
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0
 */
class LogFilter() extends EssentialFilter {
  override def apply(nextFilter: EssentialAction) = new EssentialAction {
    override def apply(rh: RequestHeader) = {
      Logger.debug(s"[LOGFILTER] rh.acceptedTypes   : ${rh.acceptedTypes}")
      Logger.debug(s"[LOGFILTER] rh.acceptLanguages : ${rh.acceptLanguages}")
      Logger.debug(s"[LOGFILTER] rh.charset         : ${rh.charset}")
      Logger.debug(s"[LOGFILTER] rh.contentType     : ${rh.contentType}")
      Logger.debug(s"[LOGFILTER] rh.cookies         : ${rh.cookies}")
      Logger.debug(s"[LOGFILTER] rh.domain          : ${rh.domain}")
      Logger.debug(s"[LOGFILTER] rh.flash           : ${rh.flash}")
      Logger.debug(s"[LOGFILTER] rh.host            : ${rh.host}")
      Logger.debug(s"[LOGFILTER] rh.mediaType       : ${rh.mediaType}")
      Logger.debug(s"[LOGFILTER] rh.rawQueryString  : ${rh.rawQueryString}")
      Logger.debug(s"[LOGFILTER] rh.session         : ${rh.session}")
      Logger.debug(s"[LOGFILTER] rh.method          : ${rh.method}")
      Logger.debug(s"[LOGFILTER] rh.path            : ${rh.path}")
      Logger.debug(s"[LOGFILTER] rh.remoteAddress   : ${rh.remoteAddress}")
      Logger.debug(s"[LOGFILTER] rh.secure          : ${rh.secure}")
      Logger.debug(s"[LOGFILTER] rh.uri             : ${rh.uri}")
      Logger.debug(s"[LOGFILTER] rh.tags            : ${rh.tags}")
      Logger.debug(s"[LOGFILTER] rh.version         : ${rh.version}")
      nextFilter(rh)
    }
  }
}
