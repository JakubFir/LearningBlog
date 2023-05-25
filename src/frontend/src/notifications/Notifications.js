import {notification} from "antd";

const openNotificationWithIcon = (type, message, description) => {
    notification[type]({message, description});
};
export const successNotification = (message, description) =>
    openNotificationWithIcon('success', message, description);

export const errorNotification = (message, description) => {
    if (message.includes("expired")) {
        message = "Session has expired, please login again"
    }
    openNotificationWithIcon('error', message, description);
}

export const infoNotification = (message, description) =>
    openNotificationWithIcon('info', message, description);

export const warningNotification = (message, description) =>
    openNotificationWithIcon('warning', message, description);
