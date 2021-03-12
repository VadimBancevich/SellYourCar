import * as axios from "axios";
import * as qs from "qs";
import {host} from "./backendInfo";


const instance = axios.create({
    withCredentials: true,
    baseURL: host + '/api/v1/'
})

export const authAPI = {
    login(email, password) {
        let data = qs.stringify({email, password});
        return instance.post('login', data, {headers: {"Content-Type": "application/x-www-form-urlencoded"}})
    },
    logout() {
        return instance.post('logout');
    },
    me() {
        return instance.get('auth/me')
    },
    registration(userDto) {
        return instance.post('auth/registration', userDto)
    },
    verifyAccount(token) {
        return instance.get('auth/verify/' + token);
    }
}

export const carApi = {
    getBrands() {
        return instance.get('brands/all');
    },
    getModelsByBrandId(brandId) {
        return instance.get('models?brandId=' + brandId);
    },
    getGenerationsByModelId(modelId) {
        return instance.get('generations?modelId=' + modelId);
    },
    findCars(formData) {
        const query = qs.stringify(formData, {indices: false})
        return instance.get('cars/find?' + query, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }, data: {}
        });
    },
    getCarById(id) {
        return instance('cars/' + id);
    },
    createCar(data) {
        let fd = new FormData();
        if (data.images) {
            let images = data.images;
            data.images = null;
            images.forEach(image => {
                fd.append("imagesFiles", image, image.name)
            })
        }
        fd.append("carDto", new Blob([JSON.stringify(data)], {type: "application/json"}))
        return instance.post('cars', fd, {headers: {"Content-Type": "multipart/form-data"}})
    },
    getMyCars() {
        return instance.get('/cars/my')
    },
    getMyCar(id) {
        return instance.get('/cars/my/' + id);
    },
    updateCar(car) {
        console.log(car);
        return instance.put('cars', car);
    }
}
export const messagesApi = {
    getChats(skip, size) {
        let query = 'chats';
        if (skip || size) {
            query += '?' + qs.stringify({skip, size});
        }
        return instance.get(query)
    },
    getMessages(chatId, skip, size) {
        if (chatId) {
            let query = 'messages?' + qs.stringify({chatId, skip, size});
            return instance.get(query);
        } else {
            console.error("Parameter chatId must be presented!")
        }
    },
    sendMessage(carId, messageText) {
        let data = qs.stringify({carId, messageText});
        instance.post('messages/send', data);
    }
}

export const chatsApi = {
    getChatById(chatId) {
        if (chatId) {
            return instance.get('chats/' + chatId)
        }
    }
}

export const imagesApi = {
    addImage(image, carId) {
        if (image && carId) {
            let fd = new FormData();
            fd.append("carId", new Blob([JSON.stringify(carId)], {type: "application/json"}));
            fd.append("image", image, image.name);
            return instance.post('cars/images/add', fd, {headers: {"Content-Type": "multipart/form-data"}})
        } else {
            console.error("image and carId must be presented")
        }
    },
    deleteImage(imageUrl, carId) {
        if (imageUrl && carId) {
            const data = qs.stringify({carId, imageUrl})
            return instance.delete('/cars/images?' + data)
        } else {
            console.error("imageUrl and carId must be presented")
        }
    }
}

