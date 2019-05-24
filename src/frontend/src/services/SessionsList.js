import Api from './Api'

export default {
  async getSeancesList () {
    return Api().get('/mes-seances')
  }
}
