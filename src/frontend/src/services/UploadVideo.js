import Api from './Api'

export default {
    async uploadVideo(formData) {
        return Api().post('/video/upload', formData,
            {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'multipart/form-data'
                }
            })
    }
}