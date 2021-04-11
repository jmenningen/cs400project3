

public class PathfindingRequest<W> {

    public enum RequestType {
        BUILDING, DINING, LIBRARY, PARKING
    }

    private Building start;
    private Building end;
    private RequestType requestType;

    public PathfindingRequest(Building start, Building end, RequestType requestType) {
        this.start = start;
        this.end = end;
        this.requestType = requestType;
    }

    public Building getStart() {
        return start;
    }

    public Building getEnd() { return end; }

    public RequestType getRequestType() {
        return requestType;
    }
}
