import request from '@/utils/request.js'

export const getContainers = () => {
    return request('/docker/container', 'GET')
}
