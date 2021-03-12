import React, {useState} from "react";
import CarImages from "../../CarPage/CarImages/CarImages";
import {connect} from "react-redux";
import {addImage, deleteSelectedImage, initImages, resetImages} from "../../../redux/reducers/imagesReducer";
import s from './AddImage.module.css'
import {locales} from "../../../locales";
import {imagesApi} from "../../../api/api";

const checkFileType = (fileType) => {
    return fileType === "image/jpeg" || fileType === "image/png"
}

const checkFileSize = (fileSize) => {
    return fileSize <= 3000000;
}

const AddImage = (props) => {

    const [incorrectFileTypeMessage, setIncorrectFileTypeMessage] = useState('');
    const [dragEnterMessage, setDragEnterMessage] = useState('');


    const {carId} = props;

    const handleFileChosen = (file) => {
        const url = URL.createObjectURL(file)
        if (carId) {
            imagesApi.addImage(file, carId).then(resp => {
                props.addImage(url)
            })
        } else {
            props.addImage(url);
            props.fields.push(file)
        }
    }

    const dragOver = (e) => {
        e.preventDefault();
    }
    const dragEnter = (e) => {
        e.preventDefault();
        if (!checkFileType(e.dataTransfer.items[0].type)) {
            setIncorrectFileTypeMessage(locales.ru.incorrectFileTypeError)
        } else {
            setIncorrectFileTypeMessage('')
            setDragEnterMessage(locales.ru.dropImageFilePrompt);
        }
    }
    const dragLeave = (e) => {
        e.preventDefault();
        setIncorrectFileTypeMessage('')
        setDragEnterMessage('')
    }
    const dragDrop = (e) => {
        e.preventDefault();
        if (!incorrectFileTypeMessage) {
            const file = e.dataTransfer.files[0];
            if (checkFileSize(file.size)) {
                handleFileChosen(file)
            } else {
                setIncorrectFileTypeMessage(locales.ru.tooLargeImageFile)
            }
        } else {
            setIncorrectFileTypeMessage('')
        }
        setDragEnterMessage('')
    }

    return (
        <div className={s.content}>
            <div onDrop={dragDrop} onDragOver={dragOver} onDragEnter={dragEnter} onDragLeave={dragLeave}
                 className={s.addImageBlock}
                 style={{color: incorrectFileTypeMessage ? "red" : dragEnterMessage ? "green" : ''}}>
                {incorrectFileTypeMessage || dragEnterMessage || locales.ru.dropImageFilePrompt}
            </div>
            <CarImages deleteImage={props.deleteSelectedImage} fields={props.fields}/>
        </div>

    )
}

const mapStateToProps = state => ({
    carId: state.updateCar.car && state.updateCar.car.id
})

export default connect(mapStateToProps, {deleteSelectedImage, addImage, resetImages, initImages})(AddImage);