package controllers;

import apimodels.Request;
import apimodels.TransformerQuery;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.*;

@SuppressWarnings("RedundantThrows")
public interface AsynchronousApiControllerImpInterface {
    Request statusRequestIdGet(String requestId) throws Exception;

    Request submitPost(TransformerQuery query) throws Exception;

}
