import { PropsWithRef } from 'react';

export interface RatesData {
    data: any[];
}

const Rates = (props: PropsWithRef<RatesData>) => {
    return (
        <>
            {props.data.map(item => (
                <div> {Object.values(item).join('---')}</div>
            ))}
        </>
    );
};
export default Rates;
