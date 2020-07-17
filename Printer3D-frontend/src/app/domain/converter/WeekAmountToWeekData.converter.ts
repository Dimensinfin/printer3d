// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { RequestForm } from '@domain/RequestForm.domain';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';
import { WeekAmount } from '@domain/dto/WeekAmount.dto';
import { WeekData } from '@domain/WeekData.domain';
import { values } from 'cypress/types/lodash';

export class WeekAmountToWeekDataConverter implements Converter<WeekAmount, WeekData>{
    convert(input: WeekAmount): WeekData {
        return new WeekData({
            name: 'Week ' + input.getWeek(),
            value: input.getAmount()
        })
    }
}
