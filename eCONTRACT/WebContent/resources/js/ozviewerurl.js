var ozviewerUrlType = "";
function setOZViewerUrlType(type) {
    ozviewerUrlType = type;
}

function getOZViewerUrlType() {
    return ozviewerUrlType;
}

function makeOZViewerUrl(param) {
    var result = "";
    switch(getOZViewerUrlType())
    {
        case "url":
            result = "/ozr/ozviewer?" + param;
            break;
        case "protocol":
        default:
            result = "ozr://ozviewer?" + param;
            break;
    }
    return result;
}
