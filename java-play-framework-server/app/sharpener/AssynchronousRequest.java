package sharpener;


import apimodels.GeneList;
import apimodels.Request;
import apimodels.TransformerQuery;

public class AssynchronousRequest implements Runnable{

	private static TimeOrderedMap<String,Request> requests = new TimeOrderedMap<String,Request>(14 * 24 * 60 * 60 * 1000/* two weeks */);

	private static IdGenerator idGenerator = new IdGenerator(9, requests);


	public static synchronized Request status(String requestId) {
		if (requests.contains(requestId))
			return requests.get(requestId);
		else
			return new Request().requestId("Error: " + requestId + " not found").status(Request.StatusEnum.FAILED);
	}


	private static synchronized Request newRequest() {
		String id = idGenerator.nextId();
		Request request = new Request();
		request.requestId(id);
		request.status(Request.StatusEnum.RUNNING);
		requests.put(id, request);
		return request;
	}


	public static Request submit(TransformerQuery query) {
		Request request = newRequest();
		Thread thread = new Thread(new AssynchronousRequest(query, request));
		thread.start();
		return request;
	}

	private final TransformerQuery query;

	private final Request request;


	AssynchronousRequest(TransformerQuery query, Request request) {
		super();
		this.query = query;
		this.request = request;
	}


	@Override
	public void run() {
		try {
			GeneList geneList = Transformers.transform(query);
			if ("Error".equals(geneList.getGeneListId())) {
				request.setStatus(Request.StatusEnum.FAILED);
			} 
			else {
				request.setGeneListId(geneList.getGeneListId());
				request.setStatus(Request.StatusEnum.SUCCESS);
			}
		} catch (Exception e) {
			request.setStatus(Request.StatusEnum.FAILED);
		}
	}

}
