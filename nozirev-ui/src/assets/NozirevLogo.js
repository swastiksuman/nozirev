import React from 'react';

function NozirevLogo({ height = 36 }) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 210 48"
      height={height}
      aria-label="Nozirev"
    >
      <text
        x="0"
        y="40"
        fontFamily="'Arial Black', 'Arial Bold', Arial, sans-serif"
        fontWeight="900"
        fontSize="42"
        fill="white"
        letterSpacing="-2"
        textDecoration="none"
        style={{ fontStretch: 'condensed' }}
      >
        nozirev
      </text>
    </svg>
  );
}

export default NozirevLogo;
