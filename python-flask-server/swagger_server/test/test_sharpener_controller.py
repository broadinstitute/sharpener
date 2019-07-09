# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from swagger_server.models.aggregation_query import AggregationQuery  # noqa: E501
from swagger_server.models.gene_list import GeneList  # noqa: E501
from swagger_server.models.transformer_info import TransformerInfo  # noqa: E501
from swagger_server.models.transformer_query import TransformerQuery  # noqa: E501
from swagger_server.test import BaseTestCase


class TestSharpenerController(BaseTestCase):
    """SharpenerController integration test stubs"""

    def test_aggregate_post(self):
        """Test case for aggregate_post

        
        """
        query = AggregationQuery()
        response = self.client.open(
            '/sharpener/aggregate',
            method='POST',
            data=json.dumps(query),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_create_gene_list_post(self):
        """Test case for create_gene_list_post

        
        """
        query = [List[str]()]
        response = self.client.open(
            '/sharpener/create_gene_list',
            method='POST',
            data=json.dumps(query),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_transform_post(self):
        """Test case for transform_post

        
        """
        query = TransformerQuery()
        response = self.client.open(
            '/sharpener/transform',
            method='POST',
            data=json.dumps(query),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_transformers_get(self):
        """Test case for transformers_get

        
        """
        response = self.client.open(
            '/sharpener/transformers',
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
