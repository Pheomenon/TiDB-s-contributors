import request from '@/utils/request'
export default {
	getPrList(Query, current, limit) {
		return request({
			url: `/list/${current}/${limit}`,
			method: 'post',
			data: Query,
		})
	},
	getMost() {
		return request({
			url: `/list/most`,
			method: 'get',
		})
	},
	getHistory(start, end) {
		return request({
			url: `/list/history/${start}/${end}`,
			method: 'get',
		})
	},
}
