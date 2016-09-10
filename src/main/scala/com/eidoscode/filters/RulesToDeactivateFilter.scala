package com.eidoscode.filters

import play.api.mvc._
import play.api._
import scala.util.control._
import scala.util.matching.Regex


/**
 * Filter to be used as a decorator design pattern to deactivate a filter in runtime.
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0
 */
class RulesToDeactivateFilter(
    csrfFilter: EssentialFilter,
    configPrefix : String = "rule")(implicit configuration: Configuration) extends EssentialFilter {

  private def configRoot = { s"eidoscode.filters.rules.${configPrefix}" }

  private def fieldsToCheck(rh: RequestHeader) = Seq(
    ("charset", rh.charset),
    ("contentType", rh.contentType),
    ("domain", Some(rh.domain)),
    ("host", Some(rh.host)),
    ("rawQueryString", Some(rh.rawQueryString)),
    ("method", Some(rh.method)),
    ("path", Some(rh.path)),
    ("remoteAddress", Some(rh.remoteAddress)),
    ("secure", Some(rh.secure.toString())),
    ("uri", Some(rh.uri)),
    ("version", Some(rh.version))
  )

  private def applyFilter(configSuffix : String, optValue : Option[String]) : Option[String] = {
    val config = s"${configRoot}.${configSuffix}"
    optValue match {
      case Some(value) => {
        configuration.getString(config, None)  match {
          case None => None
          case Some(pattern) => {
            var regex = new Regex(pattern)
            regex.findFirstIn(value)
          }
        }
      }
      case None => None
    }
  }

  override def apply(nextFilter: EssentialAction) = new EssentialAction {
    override def apply(rh: RequestHeader) = {
      val chainedFilter = csrfFilter.apply(nextFilter)
      var applyChain = true
      val loop = new Breaks
      loop.breakable {
        for (fields <- fieldsToCheck(rh)) {
          var exec = applyFilter(fields._1, fields._2)
          exec match {
            case Some(found) => {
              applyChain = false
              loop.break
            }
            case None => None
          }
        }
      }
      if (applyChain) {
        Logger.trace("Apply chain.")
        chainedFilter(rh)
      } else {
        Logger.trace("Jump to the next filter.")
        nextFilter(rh)
      }
    }
  }
}

