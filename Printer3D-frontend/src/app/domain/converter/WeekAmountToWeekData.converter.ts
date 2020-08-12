// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { WeekAmount } from '@domain/dto/WeekAmount.dto';
import { WeekData } from '@domain/WeekData.domain';

export class WeekAmountToWeekDataConverter implements Converter<WeekAmount, WeekData>{
    convert(input: WeekAmount): WeekData {
        return new WeekData({
            name: 'Week ' + input.getWeek(),
            value: input.getAmount()
        })
    }
}
