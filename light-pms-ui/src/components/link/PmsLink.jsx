import classNames from "classnames";
import './PmsLink.scss';
import {Link} from "react-router-dom";

function PmsLink({
                     children,
                     primary,
                     secondary,
                     success,
                     warning,
                     danger,
                     outline,
                     rounded,
                     ...rest
                 }) {

    const classes = classNames(
        rest.className,
        'btn', {
            'btn--primary': primary,
            'btn--secondary': secondary,
            'btn--success': success,
            'btn--warning': warning,
            'btn--danger': danger,
            'btn--primary--outline': outline && primary,
            'btn--secondary--outline': outline && secondary,
            'btn--success--outline': outline && success,
            'btn--warning--outline': outline && warning,
            'btn--danger--outline': outline && danger
        });

    return <Link className={classes} {...rest}>
        {children}
    </Link>
}

PmsLink.propTypes = {
    checkVariationValue: ({primary, secondary, success, warning, danger}) => {
        const count = Number(!!primary)
            + Number(!!secondary)
            + Number(!!success)
            + Number(!!warning)
            + Number(!!danger);
        if (count > 1) {
            return new Error('Only one of variations (primary, secondary, success, warning, danger) can be true');
        }
    }
}

export default PmsLink;