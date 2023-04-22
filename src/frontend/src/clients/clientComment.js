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

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`
    }
})


export const getAllComments = () =>
    fetch("api/v1/blog/posts/comments",)
        .then(checkStatus)

export const addCommentToPost = (postId, comment,userId) =>
    fetch(`api/v1/comments/${postId}/${userId}`, {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(comment),
    }).then(checkStatus);
