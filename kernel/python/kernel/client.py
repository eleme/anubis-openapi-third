import hashlib
import json
import time
from urllib.parse import urlencode
import const
from kernel.config import Config
from kernel.context import Context


def merge(system_params, biz_params, text_params):
    biz_params.update(system_params)
    text_params.update(biz_params)
    return text_params

def sort_map(params):
    return sorted(params.items(), key=lambda x:x[0], reverse=False)

class Client:

    context = None
    def __init__(self,context):
       self.context = context
    def get_config(self,key):
        return self.context.get_config(key)

    def get_timestamp(self):
        return int(round(time.time() * 1000))

    def to_jsonstring(self,params):
        return json.dumps(params)

    def sort_map(self,params):
        return sort_map(params)

    def read_as_json(self,teaResponse):
        return json.dumps(teaResponse.__dict__)

    def sign(self,system_params,biz_params,text_params,secret_key):
        mergeDic = merge(system_params,biz_params,text_params)
        sorted_map = sort_map(mergeDic)
        encodeStr = urlencode(sorted_map)
        x = hashlib.sha256()
        x.update((secret_key + encodeStr).encode('utf-8'))
        return x.hexdigest()

    def obj_to_jsonstring(self,obj):
        json_str = json.dumps(obj.__dict__)
        return json_str

    def to_resp_model(self,params):
        code = str(params[const.CODE])
        msg = str(params[const.MSG])
        if(code != None and code == const.SUCCESS_CODE):
            data = str(params[const.BIZ_CONTENT_FIELD])
            return json.loads(data)
        raise Exception('接口访问异常，code:{},msg:{}'.format(code,msg))
    def to_url_encoded_request_body(self,params):
        sorted_map = sort_map(params)
        return self.build_query_string(sorted_map)
    def build_query_string(self,sorted_dict):
        content = urlencode(sorted_dict)
        return content
if __name__ == '__main__':
    config = Config()
    context = Context(config)
    client = Client(context)
    dic1 = {
        'a':'2'
    }
    dic2 = {
        'b':'1'
    }
    dic3 = {
        'c':'3'
    }


    params = {
        'code':'200',
        'msg':'系统内部错误',
        'business_data':'{"a": "1"}'
    }
    client.to_resp_model(params)
    r = bytes(json.dumps(params),'utf-8')

    # print(r.decode('utf-8'))
    # content = client.build_query_string(params)
    print(urlencode(params))
    dic4 = client.sign(dic1,dic2,dic3,"123456")
    print(dic4)