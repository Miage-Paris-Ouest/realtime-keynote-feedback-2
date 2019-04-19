import Api from './Api'

export default {
    async getDashboard() {
        return Api().get('/')
    }
}