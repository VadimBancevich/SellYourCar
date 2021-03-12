import React from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import {locales} from "../../../../locales";
import CardActionArea from "@material-ui/core/CardActionArea";
import s from './CarPreview.module.css';
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import {defImg} from "../../../../redux/reducers/imagesReducer";

const CarPreview = (props) => {
    const {car, toUpdate} = props;
    const defaultImage = useSelector(state => state.images.defaultImage);

    return (
        <Card className={s.card}>
            <Link to={'/cars/' + (toUpdate ? 'my/' : '') + car.id} style={{textDecoration: 'none', color: "black"}}>
                <CardActionArea>
                    <CardHeader className={s.cardHeader}
                                title={car.brand + ' ' + car.model + ' ' + car.generation + ', ' + car.year}/>
                    <CardMedia className={s.cardMedia} src={car.images.length > 0 ? car.images[0] : defaultImage}
                               onError={event => event.target.src = defImg}
                               component={"img"}/>
                </CardActionArea>
            </Link>
            <CardContent>
                <Typography style={{fontSize: "12px"}}>
                    {
                        locales.ru.engine + ':' + locales.ru[car.engine.toLowerCase()] + ', ' +
                        locales.ru.engineVolume + ':' + car.engineVolume / 1000 + locales.ru.l + ', '
                    }
                    <br/>
                    {
                        locales.ru.transmission + ':' + locales.ru[car.transmission.toLowerCase()] + ', ' +
                        locales.ru.drivetrain + ':' + locales.ru[car.drivetrain.toLowerCase()] + ', '
                    }
                    <br/>
                    {
                        locales.ru.bodyType + ':' + locales.ru[car.bodyType.toLowerCase()] + ', ' +
                        locales.ru.mileage + ':' + car.mileage + locales.ru.km
                    }
                </Typography>
            </CardContent>
        </Card>
    )
}

export default CarPreview;