import scrapy
from prdetail.items import PrdetailItem

class prdetailSpider(scrapy.Spider):
    name = 'prdetail'
    start_urls = ['https://github.com/pingcap/tidb/pull/19220']

    def parse(self,response):
        node_list = response.xpath("//div[@class='pull-discussion-timeline js-pull-discussion-timeline js-quote-selection-container js-review-state-classes']")
        
        for node in node_list:
            #创建item字段对象，用来存储信息
            item = PrdetailItem()
            
            avatar = node.xpath("./div/div/div/a/img[@class='avatar rounded-1 avatar-user']/@src").extract()
            name = node.xpath("./div/div/div/div/div/h3/strong/a/text()").extract()
            detail = node.xpath("./div/div/div/div/div/task-lists/table[@class='d-block']/tbody/tr/td").extract()

            item['authorAvatar'] = avatar[0]
            item['authorName'] = name[0]
            item['prdetail'] = detail[0]

            #返回提取到的每个item给管道去处理
            yield item
        
        # #如果能提取到下一页的连接则说明有下一页，则将回调parse将下一页的连接作为参数
        # if len(response.xpath("//a[@class='next_page']/@href")) != 0:
        #     url = response.xpath("//a[@class='next_page']/@href").extract()[0]
        #     yield scrapy.Request("https://github.com"+url,callback = self.parse)