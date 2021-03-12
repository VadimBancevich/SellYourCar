import React, {useState} from "react";
import CarDescription from "../MainPage/CarsBlock/CarPreview/CarDescription";
import CarImages from "./CarImages/CarImages";
import {locales} from "../../locales";
import ModalMessageWindow from "./ModalMessageWindow";
import Modal from "@material-ui/core/Modal";
import s from "./CarPage.module.css"
import Button from "@material-ui/core/Button";

const CarPage = (props) => {
    const {car} = props;
    const [openModal, setOpenModal] = useState(false);
    const handleOpenModal = () => {
        setOpenModal(true);
    }
    const handleCloseModal = () => {
        setOpenModal(false);
    }

    return (
        <div className={s.carPage}>
            <CarImages/>
            <div className={s.carDescription}>
                <CarDescription car={car}/>
                <Button variant={"contained"} onClick={handleOpenModal}>{locales.ru.message}</Button>
                <Modal className={s.sendMessage}
                       onClose={handleCloseModal} children={<ModalMessageWindow onClose={handleCloseModal} carId={car.id}/>} open={openModal}/>
            </div>
        </div>
    )
}

export default CarPage;
