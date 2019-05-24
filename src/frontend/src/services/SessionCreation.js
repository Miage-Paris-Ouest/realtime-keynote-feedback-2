import Api from './Api'

export default {
  async createSession (sessionData) {
    return Api().post('/creer-seance', sessionData)
  }
}
