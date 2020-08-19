import scrapy
from prlist.items import PrlistItem

class PrlistSpider(scrapy.Spider):
    name = "prlist"
    # allowed_domains = ["https://www.github.com"]
    start_urls = ["https://github.com/pingcap/tidb/pulls?page=1&q=is%3Apr+is%3Aclosed"]

    def parse(self,response):
        node_list = response.xpath("//div[@class='js-navigation-container js-active-navigation-container']/div/div[@class='d-flex Box-row--drag-hide position-relative']/div[@class='flex-auto min-width-0 p-2 pr-3 pr-md-2']")
        
        for node in node_list:
            #创建item字段对象，用来存储信息
            item = PrlistItem()
            
            name = node.xpath("./a/text()").extract()
            link = node.xpath("./a/@href").extract()
            author = node.xpath("./div[@class='mt-1 text-small text-gray']/span[@class='opened-by']/a/text()").extract()
            tags = node.xpath("./span[@class='labels lh-default d-block d-md-inline']/a[@class='d-inline-block IssueLabel']/text()").extract()
            time = node.xpath("./div[@class='mt-1 text-small text-gray']/span[@class='opened-by']/relative-time[@class='no-wrap']/@datetime").extract()
            
            item['prName'] = name[0]
            item['prLink'] = link[0]
            item['prAuthor'] = author[0]
            if tags is not None or len(tags) != 0:
                for tag in tags:
                    item['prTags'] = tag
            item['prTime'] = time[0]

            #返回提取到的每个item给管道去处理
            yield item
        
        #如果能提取到下一页的连接则说明有下一页，则将回调parse将下一页的连接作为参数
        if len(response.xpath("//a[@class='next_page']/@href")) != 0:
            url = response.xpath("//a[@class='next_page']/@href").extract()[0]
            yield scrapy.Request("https://github.com"+url,callback = self.parse)