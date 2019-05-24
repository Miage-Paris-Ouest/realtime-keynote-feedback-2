import Api from './Api'

export default {
  async getSeanceStatistics (seanceData) {
    return Api().get('/seance-statistics', {
      id: seanceData.id
    })
  }
}
