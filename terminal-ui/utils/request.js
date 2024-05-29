const request = (url, method, data) => {
    return new Promise(function (resolve, reject) {
        let header = {
            'content-type': 'application/json'
        }
        // let token = uni.getStorageSync("token")
        // if(token != '' && typeof(token) != "undefined"){
        // 	header['Authorization'] =  'Bearer ' + token
        // }

        uni.showLoading({
            title: '数据记载中Orz',
            icon: 'loading',
            mask: true
        });
        uni.request({
            header: header,
            url: "/api" + url,
            // url: url,
            method: method,
            data: data,
            success: (res) => {

                if (res.data.status == 2003 || res.data.status == 404) {
                    // uni.removeStorageSync('token')
                    uni.redirectTo({
                        url: '/pages/index/index'
                    })
                } else if (res.data.status != 100) {
                    uni.showToast({
                        title: res.data.message,
                        icon: 'none',
                        duration: 2000
                    })
                    console.log("err=>" + res.data)
                    throw res.data.message;
                }
                resolve(res.data)
                uni.hideLoading()
            },
            fail: (res) => {
                reject(res.data)
                uni.hideLoading()
            },
            complete() {
                uni.hideLoading()
            }
        })
    })
}

export default request