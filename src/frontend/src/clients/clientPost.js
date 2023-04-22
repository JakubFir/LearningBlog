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


export const getAllPost = () =>
    fetch("/api/v1/blog/posts")
        .then(checkStatus);


export const getPostComments = postId =>
    fetch(`api/v1/comments/${postId}`)
        .then(checkStatus)




export const deletePost = postId =>
    fetch(`api/v1/blog/posts/${postId}`, {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'DELETE',
    }).then(checkStatus);


export const updatePost = (postId, updatedPost) =>
    fetch(`api/v1/blog/posts/${postId}`, {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'PUT',
        body: JSON.stringify(updatedPost),
    }).then(checkStatus)


export const addNewPost = (post,userId) =>
    fetch(`api/v1/blog/posts/${userId}`, {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(post)
    }).then(checkStatus);
