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


export const getTranslatedPosts = () =>
    fetch(`api/v1/translator`)
        .then(checkStatus)



export const translatePost = (post,postId) =>
    fetch(`api/v1/translator/${postId}`, {
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(post)
    }).then(checkStatus);
