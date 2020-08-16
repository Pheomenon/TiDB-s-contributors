# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class PrlistItem(scrapy.Item):
 
    #pr的名字
    prName = scrapy.Field()
    #pr的作者名字
    prAuthor = scrapy.Field()
    #pr的连接
    prLink = scrapy.Field()
    #pr的标签
    prTags = scrapy.Field()
    #pr的时间
    prTime = scrapy.Field()
