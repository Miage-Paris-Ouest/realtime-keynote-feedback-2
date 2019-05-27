export default {
  getTimeFromDateTime (date) {
    var timeParts = date
      .split('T')[1]
      .split('.')[0]
      .split(':')
    return `${timeParts[0]}h ${timeParts[1]}`
  },
  getDateFromDateTime (date) {
    var timeParts = date.split('T')[0].split('-')
    return `${timeParts[2]}/${timeParts[1]}/${timeParts[0]}`
  },
  getMonthsLabelsFromMonthsString (months) {
    var labels = [
      'Jan',
      'Fev',
      'Mar',
      'Avr',
      'Mai',
      'Juin',
      'Jui',
      'Aou',
      'Sep',
      'Nov',
      'Dec'
    ]
    return months.map(month => {
      return labels[parseInt(month.split('-')[1]) - 1]
    })
  }
}
