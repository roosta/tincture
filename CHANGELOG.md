# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [v0.3.3-SNAPSHOT]
### Changed
- Upgrade herb to v0.10.0, update components that used `:key` and `:group` meta
- Check spec for rgb alpha value
- Make SvgIcon public
- Upgrade dependencies
- Change Slide component to use CSSTransition thereby changing the
  animation to not wait for one component to exit before starting the
  next one

### Fixed
- Fix issue where slide component would error out when not passed a map for `:classes` prop
- Fix issue with `:on-click` handler being nil in grid component

### Added
- Expand icons
- Add opacity option to Slide component, default is false

## [v0.3.2]
### Added
- `<sub` and `>evt` shorthand functions to core

### Fixed
- Inline styles are now supported for all of tinctures components
- Issue where not having either `:item true` or `:container true`
  would throw an underlying herb error

### Changed
- Default font is changed from Raleway to Roboto.

### Breaking changed
- Default font is now not automatically injected into head. If a font
  URL is passed to `tincture.core/init!` it will get injected,
  otherwise leave it up to the developer. See
  [readme](https://github.com/roosta/tincture/blob/master/README.org)
  for more details.

## [v0.3.1]

### Changed
- Include reagent and re-frame as deps
- Add note in readme about exclusions

## [v0.3.0]
### Changed
- Update/fix `create-transition` function
- Prepare demo site
- Typography now follows latest material guidelines
- Upgrade deps
- Change casing for components to pascal case, deprecate kebab case
- Change init to a multi arity function
- Remove copy pasted macro/gradient functions, replace with inkspot dependency

### Added
- Documentation
- Tests
- Dynamic font loading

## [v0.2.0] - 2019-05-26

### Added
- Flexbox grid implementation
- Breakpoint event/sub handlers using re-frame
- CSS functions

[v0.3.3-SNAPSHOT]: https://github.com/roosta/tincture/compare/v0.3.2...HEAD
[v0.3.2]: https://github.com/roosta/tincture/compare/v0.3.1...v0.3.2
[v0.3.1]: https://github.com/roosta/tincture/compare/v0.3.0...v0.3.1
[v0.3.0]: https://github.com/roosta/tincture/compare/v0.2.0...v0.3.0
[v0.2.0]: https://github.com/roosta/tincture/compare/v0.1.8...v0.2.0
