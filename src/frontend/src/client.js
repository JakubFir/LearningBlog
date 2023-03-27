import fetch from 'unfetch';

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

export const getAllComments = () =>
    fetch("api/v1/blog/posts/comments",)
        .then(checkStatus)

export const addCommentToPost = (postId, comment,userId) =>
    fetch(`api/v1/blog/posts/${postId}/comments/${userId}`, {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(comment),
    }).then(checkStatus);

export const getPostComments = postId =>
    fetch(`api/v1/blog/posts/${postId}/comments`)
        .then(checkStatus)

export const sendEmail = mail =>
    fetch("api/v1/blog/contact", {
        ...getAuthConfig(),
        headers: {
            ...getAuthConfig().headers,
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(mail)
    }).then(checkStatus);

export const registerUser = user =>
    fetch("api/v1/blog/users", {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(user)
    }).then(checkStatus);

export const authonticateUser = user =>
    fetch("api/v1/blog/register/login", {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(user)
    }).then(checkStatus);

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
    }).then(checkStatus);