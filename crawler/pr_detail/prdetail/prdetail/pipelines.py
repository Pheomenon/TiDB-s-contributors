# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json
import scrapy
from scrapy.pipelines.images import ImagesPipeline

class FilePipline(object):
    def __init__(self):
        self.f = open("prDetail.json","wb")
    
    # 将item中的内容以JSON格式存储
    def process_item(self, item, spider):
        content = json.dumps(dict(item),ensure_ascii=False)+",\n"
        self.f.write(content.encode("utf-8"))
        return item

    def close_spider(self,spider):
        self.f.close()
    
class ImagePipeline(ImagesPipeline):


    #将头像存入磁盘
    def get_media_requests(self,item,info):
        imageLink = item['authorAvatar']
        yield scrapy.Request(imageLink,meta={'name':item['authorName']})
    
    #重命名头像
    def file_path(self, request, response=None, info=None):
        filename = request.meta['name']+".jpg"
        return filename

        
        
