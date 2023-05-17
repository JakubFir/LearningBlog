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


export const sendAnonymousCommentToKafka = (id, comment) =>
    fetch(`api/v1/messages/comments/${id}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(comment),
    }).then(checkStatus);


export const sendEmail = mail =>
    fetch("api/v1/blog/contact", {
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(mail)
    }).then(checkStatus);



