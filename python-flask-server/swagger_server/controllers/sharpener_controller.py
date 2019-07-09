import connexion
import six

from swagger_server.models.aggregation_query import AggregationQuery  # noqa: E501
from swagger_server.models.gene_list import GeneList  # noqa: E501
from swagger_server.models.transformer_info import TransformerInfo  # noqa: E501
from swagger_server.models.transformer_query import TransformerQuery  # noqa: E501
from swagger_server import util


def aggregate_post(query):  # noqa: E501
    """aggregate_post

     # noqa: E501

    :param query: transformer query
    :type query: dict | bytes

    :rtype: GeneList
    """
    if connexion.request.is_json:
        query = AggregationQuery.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def create_gene_list_post(query):  # noqa: E501
    """create_gene_list_post

     # noqa: E501

    :param query: transformer query
    :type query: List[]

    :rtype: GeneList
    """
    return 'do some magic!'


def transform_post(query):  # noqa: E501
    """transform_post

     # noqa: E501

    :param query: transformer query
    :type query: dict | bytes

    :rtype: GeneList
    """
    if connexion.request.is_json:
        query = TransformerQuery.from_dict(connexion.request.get_json())  # noqa: E501
    return 'do some magic!'


def transformers_get():  # noqa: E501
    """transformers_get

     # noqa: E501


    :rtype: List[TransformerInfo]
    """
    return 'do some magic!'
