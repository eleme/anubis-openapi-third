from Tea.model import TeaModel


class Context:
    config = None
    def __init__(self,config):
        self.config = TeaModel.to_map(config)
    def get_config(self,key):
        if(self.config[key] == None):
            return None
        else:
            return str(self.config[key])
