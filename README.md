# Slumber

Sleep with progress. Slumber accelerates the entire server tick while everyone sleeps — not just time-of-day. Blocks, entities, block entities, random ticks, scheduled updates, weather, raids… everything advances across all dimensions during sleep.

## Features
- **Full world ticking:** Runs extra full ticks so blocks, entities, and schedules progress.
- **All dimensions:** Applies to every loaded dimension (Overworld, Nether, End, modded).
- **Server + singleplayer:** Works on dedicated servers and integrated singleplayer.
- **All-players requirement:** Only activates when all non‑spectator players are sleeping.
- **No instant skip:** Temporarily disables vanilla night‑skip while simulating; restores it after.
- **Configurable speed:** Controlled by a gamerule for quick per‑world tuning.

## How It Works
- **Detection:** On each server tick, the mod checks whether all non‑spectator players are sleeping.
- **Acceleration:** While true, it runs extra world ticks for every `ServerLevel`, effectively multiplying server speed during sleep.
- **Sync:** Iterates `server.getAllLevels()` so time and progression stay consistent across dimensions.
- **Night‑skip handling:** Sets `playersSleepingPercentage` to `101` while active to prevent vanilla’s instant skip; restores the original value when sleep ends or when it becomes day in the Overworld.

## Configuration
- **Gamerule `slumberTickMultiplier` (integer, default `20`):**
  - Total server tick multiplier while all players sleep.
  - Example: `20` → 1 normal tick + 19 extra ticks per server tick (≈20× speed).
  - Minimum effective value is `1` (no acceleration).

### Commands
- Set multiplier: `/gamerule slumberTickMultiplier 30`
- Read current: `/gamerule slumberTickMultiplier`

## Requirements
- **Minecraft:** 1.21.8
- **Loader:** NeoForge (matching your MC version)

## Installation
- **Client (singleplayer/LAN):** Drop the JAR into your `mods/` folder.
- **Server:** Drop the JAR into the server’s `mods/` folder. All connected clients only need to join a NeoForge server; this mod runs server‑side and does not add items/blocks.

## Performance Notes
- **Multiplier cost:** The higher the `slumberTickMultiplier`, the more work per real‑time second. Large multipliers with many loaded chunks can impact MSPT.
- **Recommendation:** Start with `20` and adjust. Consider reducing if the server approaches 50+ MSPT during sleep.

## Multiplayer Behavior
- **Activation rule:** Every non‑spectator player in the server must be sleeping. Spectators are ignored for this check.
- **Partial sleep:** If any non‑spectator is awake, Slumber does nothing and vanilla behavior applies.

## Compatibility
- Designed to be non‑invasive: it uses NeoForge event hooks to run extra world ticks, without coremodding.
- Should be compatible with most mods. Extremely heavy tick‑based mods may amplify the cost of higher multipliers.

## FAQ
- **Does this change loot or gameplay rules?** No. It only changes how many world ticks run while everyone sleeps.
- **Does it force morning instantly?** No. Vanilla fast‑forward is suppressed during the simulation. Once it’s naturally day (or someone wakes), the original threshold is restored and vanilla proceeds.
- **Does it affect only Overworld?** No. All loaded dimensions tick equally.

## Contributing
Issues and PRs are welcome. Please include logs and steps to reproduce performance or compatibility problems.

## License
See `LICENSE.md` for licensing information.

