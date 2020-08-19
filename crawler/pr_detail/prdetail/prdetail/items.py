# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class PrdetailItem(scrapy.Item):
    #作者头像
    authorAvatar = scrapy.Field()
    #作者昵称
    authorName = scrapy.Field()
    #pr详细内容
    prdetail = scrapy.Field()
    #pr的标题
    prTitle = scrapy.Field()
