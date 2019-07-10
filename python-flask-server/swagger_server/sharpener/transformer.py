
from swagger_server.models.transformer_info import TransformerInfo

import requests

TRANSFORMERS = {}

def transformer_list():
    transformers = []
    with open('transformers.txt','r') as transformer_file:
        for transformer_url in transformer_file:
            transformer_url = transformer_url.strip()
            try:
                r = requests.get(transformer_url+'/transformer_info')
                if r.status_code == 200:
                    transformer_info = r.json()
                    transformer = TransformerInfo.from_dict(transformer_info)
                    transformers.append(transformer)
                else:
                    print('WARNING: unable to access '+transformer_url)
            except Exception:
                print('WARNING: unable to access '+transformer_url)
    TRANSFORMERS = {transformer.name:transformer for transformer in transformers}
    return transformers

