import fetch from "unfetch";

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}
export const addAnonymousCommentToPost = (postId, comment,commentId) =>
    fetch(`api/v1/comments/anonymous/${postId}/${commentId}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(comment)
    }).then(checkStatus);


export const getAnonymousComment = () =>
    fetch(`api/v1/comments/anonymous`)
        .then(checkStatus)

