import Api from './Api'

export default {
  async createSession (sessionData) {
    console.log(sessionData);
    return Api().post('/seance/create', sessionData)
  }
}
