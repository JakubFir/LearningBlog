import fetch from "unfetch";

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}
export const addAnonymousCommentToPost = (postId, comment) =>
    fetch(`api/v1/comments/anonymous/${postId}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(comment)
    }).then(checkStatus);


export const getAnonymousComment = () =>
    fetch(`api/v1/comments/anonymous`)
        .then(checkStatus)

