'use strict';

var moment = require('moment-timezone')

exports.get = function getDayOfWeek(date, srcTZ, destTZ){
  var momentDate
  if (!srcTZ && !destTZ) momentDate = moment(date)
  else momentDate = moment.tz(date, srcTZ).tz(destTZ || srcTZ)

  return parseInt(momentDate.format('d'), 10)
}
