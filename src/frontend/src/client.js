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

export const getAllPost = () =>
    fetch("api/v1/blog/posts")
        .then(checkStatus)

export const getAllComments = () =>
    fetch("api/v1/blog/posts/comments")
        .then(checkStatus)

export const addCommentToPost = (postId, comment) =>
    fetch(`api/v1/blog/posts/${postId}/comments`, {
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(comment),
    });

export const getPostComments = postId =>
    fetch(`api/v1/blog/posts/${postId}/comments`)
        .then(checkStatus)

export const sendEmail = mail =>
    fetch("api/v1/blog/contact", {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(mail)
    });

export const addNewPost = post =>
    fetch("api/v1/blog/posts", {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(post)
    });

export const deletePost = postId =>
    fetch(`api/v1/blog/posts/${postId}`, {
        method: 'DELETE',
    }).then(checkStatus);

export const updatePost = (postId, updatedPost) =>
    fetch(`api/v1/blog/posts/${postId}`, {
        method: 'PUT',
        body: JSON.stringify(updatedPost),
        headers: {'Content-Type': 'application/json'}
    }).then(checkStatus);