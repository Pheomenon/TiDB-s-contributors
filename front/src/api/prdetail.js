import request from '@/utils/request'
export default {
	getPrList(Query, current, limit) {
		return request({
			url: `/detail/${current}/${limit}`,
			method: 'post',
			data: Query,
		})
	},
}
