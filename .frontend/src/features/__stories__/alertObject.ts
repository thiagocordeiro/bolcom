export default function (object: object): void {
  const message = Object.keys(object)
    // @ts-ignore
    .map((it) => `${it}: ${object[it]}`)
    .join('\n')

  alert(message)
}
